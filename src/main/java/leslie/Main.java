package leslie;

import leslie.wiki.impl.MWApiWiki;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public final class Main {
  public static void main(final String[] args) {
    final Options options = new Options();
    final CmdLineParser parser = new CmdLineParser(options);
    try {
      parser.parseArgument(args);
      final Leslie leslie = new Leslie(options, new MWApiWiki(options.getWiki().toExternalForm()));
      leslie.updateTargetPage();
    } catch (final CmdLineException e) {
      System.err.println(e.getMessage());
      parser.printUsage(System.err);
    } catch (final LeslieException e) {
      System.err.println(e.getMessage());
    }
  }
}
