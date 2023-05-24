package br.ufma.ecp;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

// import java.io.IOException;

import org.junit.Test;
// import org.junit.experimental.theories.suppliers.TestedOn;

public class ParserTest extends TestSupport {

  @Test
  public void testParseTermInteger() {
    var input = "10;";
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    parser.parseTerm();
    var expectedResult = """
        <term>
        <integerConstant> 10 </integerConstant>
        </term>
        """;

    var result = parser.XMLOutput();
    expectedResult = expectedResult.replaceAll("  ", "");
    result = result.replaceAll("\r", ""); // no codigo em linux não tem o retorno de carro
    assertEquals(expectedResult, result);

  }

  @Test
  public void testParseTermIdentifer() {
    var input = "varName;";
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    parser.parseTerm();

    var expectedResult = """
        <term>
        <identifier> varName </identifier>
        </term>
        """;

    var result = parser.XMLOutput();
    expectedResult = expectedResult.replaceAll("  ", "");
    result = result.replaceAll("\r", ""); // no codigo em linux não tem o retorno de carro
    assertEquals(expectedResult, result);

  }

  @Test
  public void testParseExpressionSimple() {
    var input = "10+20";
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    parser.parseExpression();

    var expectedResult = """
        <expression>
        <term>
        <integerConstant> 10 </integerConstant>
        </term>
        <symbol> + </symbol>
        <term>
        <integerConstant> 20 </integerConstant>
        </term>
        </expression>
        """;

    var result = parser.XMLOutput();
    result = result.replaceAll("\r", "");
    expectedResult = expectedResult.replaceAll("  ", "");
    assertEquals(expectedResult, result);

  }

  @Test
  public void testParseLetSimple() {
    var input = "let var1 = 10+20;";
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    parser.parseLet();
    var expectedResult = """
          <letStatement>
            <keyword> let </keyword>
            <identifier> var1 </identifier>
            <symbol> = </symbol>
            <expression>
              <term>
              <integerConstant> 10 </integerConstant>
              </term>
              <symbol> + </symbol>
              <term>
              <integerConstant> 20 </integerConstant>
              </term>
              </expression>
            <symbol> ; </symbol>
          </letStatement>
        """;
    var result = parser.XMLOutput();
    expectedResult = expectedResult.replaceAll("  ", "");
    result = result.replaceAll("\r", ""); // no codigo em linux não tem o retorno de carro
    assertEquals(expectedResult, result);
  }


  @Test
  public void testParseSimpleClass() {
    var input = "class Main();";
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    var result = parser.XMLOutput();
    System.out.println(result);
  }

  // github do professor


  @Test
  public void testVarDeclaration() {
    var input = """
        class Point {
          field int x, y;
          constructor Point new(int Ax, int Ay) {
            var int Ax;

            let x = Ax;
            let y = Ay;
            return this;
         }
        }
        """;
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    parser.parse();
    var result = parser.XMLOutput();
    System.out.println(result);
  }


  @Test
  public void testParseTermString() {
    var input = "\"Hello World\"";
    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
    parser.parseTerm();

    var expectedResult = """
        <term>
        <stringConstant> Hello World </stringConstant>
        </term>
        """;

    var result = parser.XMLOutput();
    expectedResult = expectedResult.replaceAll("  ", "");
    result = result.replaceAll("\r", ""); // no codigo em linux não tem o retorno de carro
    assertEquals(expectedResult, result);
  }
  
}