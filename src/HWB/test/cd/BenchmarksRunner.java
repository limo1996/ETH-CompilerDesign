package cd;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cd.backend.interpreter.DynamicError;
import cd.backend.interpreter.Interpreter;
import cd.backend.interpreter.StaticError;
import cd.frontend.parser.ParseFailure;
import cd.frontend.semantic.SemanticFailure;
import cd.ir.Ast.ClassDecl;
import cd.util.FileUtil;

public class BenchmarksRunner {

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

	public BenchmarksRunner() {
		benchmarks = collectBenchs();
	}

	private static Collection<File> collectBenchs() {
		List<File> result = new ArrayList<>();
		for (File file : FileUtil.findJavaliFiles(BENCH_DIR))
			result.add(file);

		return result;
	}

	public void runBenchmarks() {
		System.out.println("Benchmark\tRuntime reduction");
		for (File file : benchmarks) {
			this.main = new Main();
			this.main.debug = new StringWriter();

			this.sFile = new File(file.getPath() + Config.ASMEXT);
			this.binFile = new File(file.getPath() + Config.BINARYEXT);
			this.inFile = new File(file.getPath() + ".in");
			this.execRefFile = new File(file.getPath() + ".exec.ref");
			this.optRefFile = new File(file.getPath() + ".opt.ref");

			System.out.print(file.getName() + "\t");
			try {
				double improvement = runOneBench(file);
				System.out.printf("%.1f%%\n", improvement * 100);
			} catch (BenchmarkError e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void runReference(File file) throws IOException, InterruptedException {
		String slash = File.separator;
		String colon = File.pathSeparator;
		String javaExe = System.getProperty("java.home") + slash + "bin" + slash + Config.JAVA_EXE;

		ProcessBuilder pb = new ProcessBuilder(javaExe, "-Dcd.meta_hidden.Version=BENCH", "-cp",
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

	private double runOneBench(File file) throws BenchmarkError {

		// Delete intermediate files from previous runs:
		if (sFile.exists())
			sFile.delete();
		if (binFile.exists())
			binFile.delete();

		String input = "";
		try {
			if (inFile.exists())
				input = FileUtil.read(inFile);
		} catch (IOException e1) {
			throw new BenchmarkError("Error while reading .in file");
		}

		String execRef, optRef;
		try {
			runReference(file);
			execRef = FileUtil.read(execRefFile);
			optRef = FileUtil.read(optRefFile);
		} catch (IOException | InterruptedException e) {
			System.err.println(e);
			throw new BenchmarkError("Error while running on reference");
		}

		List<ClassDecl> astRoots;
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

		final StringWriter outputWriter = new StringWriter();
		final Reader inputReader = new StringReader(input);
		final Interpreter interp = new Interpreter(astRoots, inputReader, outputWriter);

		try {
			interp.execute();
		} catch (StaticError | DynamicError err) {
			System.err.println(err);
			throw new BenchmarkError("Error while running the interpreter");
		}

		String execOut = outputWriter.toString();
		if (!execOut.equals(execRef))
			throw new BenchmarkError("Output is incorrect");
			
		String optOut = interp.operationSummary();
		double refCount = getTotalOps(optRef);
		double outCount = getTotalOps(optOut);
		
		if (refCount == 0 && outCount == 0)
			return 0;
		
		return 1 - outCount / refCount;
	}

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
