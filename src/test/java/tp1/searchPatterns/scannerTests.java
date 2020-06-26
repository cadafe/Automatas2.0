package tp1.searchPatterns;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.automatas.Alphabet;
import tp1.automatas.AutomatonException;
import tp1.automatas.DFA;
import tp1.automatas.State;
import tp1.automatas.StateSet;
import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class scannerTests {
	
	private static DFA dfa =null;
	private static DFA dfa2 =null;	
	private static DFA dfa3 =null;	
	private static DFA dfa4 =null;	

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("test/pattern-dfa");
		dotReader.parse();
		
		StateSet s = dotReader.getNodes();
		Alphabet a= dotReader.getSymbols();
		Set<Tupla<State,Character,State>> t = dotReader.getArcs(); 
		dfa = new DFA(s,a,t);
		
		DotReader dotReader2 = new DotReader("test/pattern-dfa2");
		dotReader2.parse();
		
		StateSet s2 = dotReader2.getNodes();
		Alphabet a2= dotReader2.getSymbols();
		Set<Tupla<State,Character,State>> t2 = dotReader2.getArcs(); 
		dfa2 = new DFA(s2,a2,t2);
		
		DotReader dotReader3 = new DotReader("test/pattern-dfa3");
		dotReader3.parse();
		
		StateSet s3 = dotReader3.getNodes();
		Alphabet a3= dotReader3.getSymbols();
		Set<Tupla<State,Character,State>> t3 = dotReader3.getArcs(); 
		dfa3 = new DFA(s3,a3,t3);
		
		
		DotReader dotReader4 = new DotReader("test/pattern-dfa4");
		dotReader4.parse();
		
		StateSet s4 = dotReader4.getNodes();
		Alphabet a4= dotReader4.getSymbols();
		Set<Tupla<State,Character,State>> t4 = dotReader4.getArcs(); 
		dfa4 = new DFA(s4,a4,t4);
		
	}

	@Test
	public void test1() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa);
		dfalist.add(dfa2);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		String result = scanner.scanFile();
		STATUS status = scanner.showStatus();
		System.out.println(status + ": " + result);
		assertTrue(status == STATUS.SUSPICIOUS && result != null);
	}
	
	@Test
	public void test2() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa3);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		String result = scanner.scanFile();
		STATUS status = scanner.showStatus();
		System.out.println(status);
		assertTrue(status == STATUS.OK && result ==null);

	}
	

	
	@Test
	public void test3() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa3);
		dfalist.add(dfa);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		String result = scanner.scanFile();
		STATUS status = scanner.showStatus();
		System.out.println(status + ": " + result);
		assertTrue(status == STATUS.SUSPICIOUS && result != null);
	}
	
	@Test
	public void test4() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa3);
		dfalist.add(dfa);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		STATUS status = scanner.showStatus();
		System.out.println(status);
		assertTrue(status == STATUS.NONSCANNED);
	}
	
	@Test
	public void test5() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa4);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		String result = scanner.scanFile();
		STATUS status = scanner.showStatus();
		System.out.println(status);
		assertTrue(status == STATUS.OK && result ==null);

	}
	
	
	@Test
	public void test6() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa4);
		dfalist.add(dfa3);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		String result = scanner.scanFile();
		STATUS status = scanner.showStatus();
		System.out.println(status);
		assertTrue(status == STATUS.OK && result ==null);

	}
	
	@Test
	public void test7() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		dfalist.add(dfa4);
		dfalist.add(dfa3);
		dfalist.add(dfa);
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		String result = scanner.scanFile();
		STATUS status = scanner.showStatus();
		System.out.println(status + ": " + result);
		assertTrue(status == STATUS.SUSPICIOUS && result !=null);

	}
	
	@Test
	public void test9() throws IOException, AutomatonException {
		ArrayList<DFA> dfalist = new ArrayList<DFA>();
		PatternList list = new PatternList(dfalist);
		Scanner scanner = new Scanner("test/prueba.o", list);
		scanner.printFileToHex();
	}
	
	
}

