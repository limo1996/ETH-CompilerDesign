package cd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import cd.frontend.parser.ParseFailure;
import cd.ir.Ast.ClassDecl;
import cd.util.FileUtil;
import cd.util.debug.AstDump;

abstract public class AbstractTestAgainstFrozenReference {
    
	public static final String SEMANTIC_OK = "OK";
    public static final String PARSE_FAILURE = "ParseFailure";
	
    public File file, sfile, binfile, infile;
	public File parserreffile, semanticreffile, execreffile, cfgreffile, rdreffile,
	        nnreffile, optreffile;
	public File errfile;
	public Main main;

	public static int counter = 0;

	@Rule
	public TestRule timeout = new DisableOnDebug(new Timeout(15, TimeUnit.SECONDS));
	
	@Test
	public void test() throws Throwable {
		System.err.println("[" + counter++ + " = " + file + "]");
		
		try {
			// Delete intermediate files from previous runs:
			if (sfile.exists())
				sfile.delete();
			if (binfile.exists())
				binfile.delete();

			runReference();
			
			List<ClassDecl> astRoots = testParser();
			if (astRoots != null) {
			}
		} catch (org.junit.ComparisonFailure cf) {
			throw cf;
		} catch (Throwable e) {
			PrintStream err = new PrintStream(errfile);
			err.println("Debug information for file: " + this.file);
			err.println(this.main.debug.toString());
			err.println("Test failed because an exception was thrown:");
			err.println("    " + e.getLocalizedMessage());
			err.println("Stack trace:");
			e.printStackTrace(err);
			System.err.println(FileUtil.read(errfile));
			throw e;
		}

		// if we get here, then the test passed, so delete the errfile:
		// (which has been accumulating debug output etc)
		if (errfile.exists())
			errfile.delete();
	}

	private void runReference() throws IOException, InterruptedException {
        String slash = File.separator;
        String colon = File.pathSeparator;
        String javaExe = System.getProperty("java.home") + slash + "bin" + slash + Config.JAVA_EXE;
        
        ProcessBuilder pb = new ProcessBuilder(
                javaExe, "-Dcd.meta_hidden.Version=" + referenceVersion(),
                "-cp", "lib/frozenReferenceObf.jar" + colon + " lib/junit-4.12.jar" + colon + "lib/antlr-4.7.1-complete.jar",
                "cd.FrozenReferenceMain", file.getAbsolutePath());
	        
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
	
	private static String referenceVersion() {
        {
            return "CD_HW_PARSER_SOL";
        }
	}

	private String tryReadRefFile(File fileToFind) {
		if (fileToFind.exists() && fileToFind.lastModified() >= file.lastModified()) {
			try {
				return FileUtil.read(fileToFind);
			} catch (IOException e) {
				throw new RuntimeException("ERROR: could not read file " + fileToFind.getPath());
			}
		}
		throw new RuntimeException("ERROR: could not find file " + fileToFind.getPath());
	}
	
	/** Run the parser and compare the output against the reference results */
	private List<ClassDecl> testParser() throws Exception {
		String parserRef = tryReadRefFile(parserreffile);
		List<ClassDecl> astRoots = null;
		String parserOut;

		try {
			astRoots = main.parse(new FileReader(this.file));

			parserOut = AstDump.toString(astRoots);
		} catch (ParseFailure pf) {
			{
				// Parse errors are ok too.
				main.debug("");
				main.debug("");
				main.debug("%s", pf.toString());
				parserOut = PARSE_FAILURE;
			}
		}

		{
			assertEquals("parser", parserRef, parserOut);
		}
		return astRoots;
	}


	private void assertEquals(String phase, String exp, String act_) {
		String act = act_.replace("\r\n", "\n"); // for windows machines
		if (!exp.equals(act)) {
			warnAboutDiff(phase, exp, act);
		}
	}

	private void warnAboutDiff(String phase, String exp, String act) {
		try {
			try (PrintStream err = new PrintStream(errfile)) {
				err.println("Debug information for file: " + this.file);
				err.println(this.main.debug.toString());
				err.println(String.format(
						"Phase %s failed because we expected to see:", phase));
				err.println(exp);
				err.println("But we actually saw:");
				err.println(act);
				err.println("The difference is:");
				err.println(Diff.computeDiff(exp, act));
			}
		} catch (FileNotFoundException exc) {
			System.err.println("Unable to write debug output to " + errfile
					+ ":");
			exc.printStackTrace();
		}
		Assert.assertEquals(
				String.format("Phase %s for %s failed!", phase,
						file.getPath()), exp, act);
	}
	
}
