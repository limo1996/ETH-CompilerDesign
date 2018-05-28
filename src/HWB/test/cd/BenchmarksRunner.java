package cd;

import static java.nio.file.Files.lines;
import static java.nio.file.Files.write;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cd.backend.codegen.CfgCodeGenerator;
import cd.backend.interpreter.DynamicError;
import cd.backend.interpreter.Interpreter;
import cd.backend.interpreter.StaticError;
import cd.frontend.parser.ParseFailure;
import cd.frontend.semantic.SemanticFailure;
import cd.ir.Ast.ClassDecl;
import cd.util.FileUtil;
import cd.util.Pair;

public class BenchmarksRunner {

	// MAIN_OVERHEAD is an estimate of instructions executed by an empty 'main' in Javali.
	private static final long MAIN_OVERHEAD = 150000;
	
	public static final File BENCH_DIR = new File("benchmarks");

	public static void main(String[] args) {
		BenchmarksRunner runner = new BenchmarksRunner();
		runner.runBenchmarks();
	}

	private final Collection<File> benchmarks;
	private File sFile;
	private File binFile;
	private File inFile;
	private Main main;
	private File optRefFile;
	private File execRefFile;
	private File sRefFile;
	private File binRefFile;

	public BenchmarksRunner() {
		benchmarks = collectBenchs();
	}

	/**
	 * Return a list of all Javali-files in a directory (recursively)
	 */
	private static Collection<File> collectBenchs() {
		List<File> result = new ArrayList<>();
		for (File file : FileUtil.findJavaliFiles(BENCH_DIR))
			result.add(file);

		return result;
	}

	/**
	 * Run all benchmarks found in <code>BENCH_DIR</code> and compare with the reference compiler.
	 */
	public void runBenchmarks() {
		System.out.println("Benchmark\tAST reduction\tValgrind reduction");
		for (File file : benchmarks) {
			this.sFile = new File(file.getPath() + Config.ASMEXT);
			this.binFile = new File(file.getPath() + Config.BINARYEXT);
			this.inFile = new File(file.getPath() + ".in");
			this.execRefFile = new File(file.getPath() + ".exec.ref");
			this.optRefFile = new File(file.getPath() + ".opt.ref");
			this.sRefFile = new File(file.getPath() + ".ref" + Config.ASMEXT);
			this.binRefFile = new File(file.getPath() + ".ref" + Config.BINARYEXT);

			System.out.print(file.getName() + "\t");
			try {
				Pair<Double> improvement = runOneBench(file);
				System.out.printf("%.1f%%\t%.1f%%\n", 
						improvement.a * 100, improvement.b * 100);
			} catch (BenchmarkError e) {
				System.out.println(e.getMessage());
			} catch (Throwable t) {
				System.out.println("ERROR");
			}
		}
	}

	/**
	 * Run a Javali-program on the reference compiler
	 */
	private void runReference(File file) throws IOException, InterruptedException {
		String slash = File.separator;
		String colon = File.pathSeparator;
		String javaExe = System.getProperty("java.home") + slash + "bin" + slash + Config.JAVA_EXE;

		String benchV = "BENCH2";
		
		ProcessBuilder pb = new ProcessBuilder(javaExe, 
				"-Dcd.meta_hidden.Version="+benchV, "-cp",
				"lib/frozenReferenceObf.jar" + colon + " lib/junit-4.12.jar" + colon
						+ "lib/antlr-4.7.1-complete.jar",
				"cd.FrozenReferenceMain", file.getAbsolutePath());

		pb.redirectOutput(Redirect.INHERIT);
		pb.redirectError(Redirect.INHERIT);

		Process proc = pb.start();
		proc.waitFor();
		try (InputStream err = proc.getErrorStream()) {
			if (err.available() > 0) {
				byte b[] = new byte[err.available()];
				err.read(b, 0, b.length);
				System.err.println(new String(b));
			}
		}
	}

	/**
	 * Run and compare one javali-benchmark. Returns two "improvement" numbers that
	 * represent the reduction in instructions executed. (once on the interpreter, once
	 * as compiled code using valgrind)
	 */
	private Pair<Double> runOneBench(File file) throws BenchmarkError {
		// Delete intermediate files from previous runs:
		if (sFile.exists())
			sFile.delete();
		if (binFile.exists())
			binFile.delete();

		// Read test-input file
		String input = "";
		try {
			if (inFile.exists())
				input = FileUtil.read(inFile);
		} catch (IOException e1) {
			throw new BenchmarkError("Error while reading .in file");
		}

		// Run test on reference implementation
		String execRef, optRef;
		try {
			runReference(file);
			execRef = FileUtil.read(execRefFile);
			optRef = FileUtil.read(optRefFile);
		} catch (IOException | InterruptedException e) {
			System.err.println(e);
			throw new BenchmarkError("Error while running on reference");
		}

		// Call frontend of compiler-under-test (CUT)
		List<ClassDecl> astRoots;
		PrintStream oldStdOut = System.out;
		try {
			System.setOut(new PrintStream(new File("stdout-log.txt")));
			
			this.main = new Main();
			this.main.debug = new StringWriter();
			
			try {
				astRoots = main.parse(new FileReader(file));
			} catch (ParseFailure | IOException pf) {
				System.err.println(pf);
				throw new BenchmarkError("ParseFailure");
			}
			
			try {
				main.semanticCheck(astRoots);
			} catch (SemanticFailure sf) {
				System.err.println(sf);
				throw new BenchmarkError("SemanticFailure");
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
			throw new BenchmarkError("Failed to redirect stdout");
		} finally {
			System.setOut(oldStdOut);
		}

		// Run IR of compiled test with interpreter to count
		// instructions
		final StringWriter outputWriter = new StringWriter();
		final Reader inputReader = new StringReader(input);
		final Interpreter interp = new Interpreter(astRoots, inputReader, outputWriter);

		try {
			interp.execute();
		} catch (StaticError | DynamicError err) {
			System.err.println(err);
			throw new BenchmarkError("Error while running the interpreter");
		}

		// If output is the same, calculate the reduction in
		// instructions by the CUT compared to the reference
		// compiler
		String execOut = outputWriter.toString();
		if (!execOut.equals(execRef))
			throw new BenchmarkError("Output is incorrect");

		String optOut = interp.operationSummary();
		double refCount = getTotalOps(optRef);
		double outCount = getTotalOps(optOut);

		double interpReduction = 0;
		if (refCount != 0 || outCount != 0)
			interpReduction = 1 - outCount / refCount;
		
		double valgrindReduction = 0;
		// on BENCH2, we also compare the generated assembly using valgrind
		valgrindReduction = compareWithValgrind(astRoots, input);
		
		return new Pair<>(interpReduction, valgrindReduction);
	}

	
	/**
	 * Generate assembly, assemble, and run the executables of the reference 
	 * and the CUT on valgrind to get a deterministic instruction count. Return
	 * the reduction of instructions compared to the reference on BENCH2.
	 */
	private double compareWithValgrind(List<ClassDecl> astRoots, String input) throws BenchmarkError {
		// generate ASM
		try (FileWriter fout = new FileWriter(sFile)) {
			CfgCodeGenerator cg = new CfgCodeGenerator(main, fout);
			cg.go(astRoots);
		} catch (IOException e) {
			System.err.println(e);
			throw new BenchmarkError("IOException when generating ASM");
		}
		
		// assemble the generated and the reference's assembly file
		try {
			FileUtil.runCommand(
					Config.ASM_DIR, Config.ASM,
					new String[] { binFile.getAbsolutePath(), sFile.getAbsolutePath() },
					null, false);
			if (!binFile.exists())
				throw new BenchmarkError("Error while assembling ASM");
		} catch (IOException e) {
			System.err.println(e);
			throw new BenchmarkError("Error while assembling ASM");
		}
		
		try {
			FileUtil.runCommand(
					Config.ASM_DIR, Config.ASM,
					new String[] { binRefFile.getAbsolutePath(), sRefFile.getAbsolutePath() },
					null, false);
			if (!binRefFile.exists())
				throw new BenchmarkError("Error while assembling ASM from reference");
		} catch (IOException e) {
			System.err.println(e);
			throw new BenchmarkError("Error while assembling ASM from reference");
		}
		
		// run both binaries on valgrind
		String execOut;
		try {
			String[] cmd = {"valgrind", "--tool=callgrind", binFile.getAbsolutePath()};
			execOut = FileUtil.runCommand(new File("."),
					cmd, new String[] {},
					input, true);
		} catch (IOException e) {
			System.err.println(e);
			throw new BenchmarkError("Error while running binary");
		}

		String execRefOut;
		try {
			String[] cmd = {"valgrind", "--tool=callgrind", binRefFile.getAbsolutePath()};
			execRefOut = FileUtil.runCommand(new File("."),
					cmd, new String[] {},
					input, true);
		} catch (IOException e) {
			System.err.println(e);
			throw new BenchmarkError("Error while running binary from reference");
		}
		
		// parse output of valgrind and return reduction in instructions
		double instrCount = parseInstrCount(execOut) - MAIN_OVERHEAD;
		double instrRefCount = parseInstrCount(execRefOut) - MAIN_OVERHEAD;
		
		double valgrindReduction = 0;
		if (instrCount != 0 || instrRefCount != 0)
			valgrindReduction = 1 - instrCount / instrRefCount;
		
		return valgrindReduction;
	}

	/**
	 * Parse output of valgrind and return total instruction count
	 */
	private long parseInstrCount(String execOut_) throws BenchmarkError {
		String execOut = execOut_.replace("\r\n", "\n");
		for (String line : execOut.split("\n")) {
			if (line.contains(" I   refs:")) {
				String[] args = line.split(": ");
				String number = args[1].trim();
				number = number.replace(",", "");
				return Long.valueOf(number);
			}
		}

		throw new BenchmarkError("Error while parsing valgrind output");
	}

	/**
	 * Parse operation summary of interpreter and get the total count of instructions executed
	 * (Binary+Unary ops)
	 */
	private int getTotalOps(String optCount_) {
		String optCount = optCount_.replace("\r\n", "\n");
		int sum = 0;
		for (String line : optCount.split("\n")) {
			if (!line.equals("")) {
				String[] args = line.split(": ");
				sum += Integer.valueOf(args[1]);
			}
		}

		return sum;
	}

	class BenchmarkError extends Exception {
		private static final long serialVersionUID = 1L;

		public BenchmarkError(String arg) {
			super(arg);
		}
	}
}
