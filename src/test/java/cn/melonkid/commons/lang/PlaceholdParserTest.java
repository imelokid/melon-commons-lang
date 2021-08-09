package cn.melonkid.commons.lang;

import cn.melonkid.commons.lang.parser.PlaceholdParser;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * 占位符解析测试类
 *
 * @author imelonkid
 * @date 2021/08/07 12:22
 **/
public class PlaceholdParserTest {


  @Test
  public void testParse() {
    String testStr = "this is a test, h1:{} h2:{} h3{";
    String finalStr = PlaceholdParser.parse(testStr, "p1","p2");
    assert "this is a test, h1:p1 h2:p2 h3{".equals(finalStr);

    String testStr1 = "this is a test, h1:{} h2:{} h3:{}";
    String finalStr1 = PlaceholdParser.parse(testStr1, "p1","p2");
    assert "this is a test, h1:p1 h2:p2 h3:".equals(finalStr1);
  }


  @Test
  public void testParseAndReplaceByName(){
    String testStr = "this is a test, h1:{} h2:${test} h3:${test1} h4:${} h5:${test2} h6:${";
    Map<String, String> params = new HashMap<>();
    params.put("test", "melonkid");
    params.put("test2", "");
    String finalStr = PlaceholdParser.parseAndReplaceByName(testStr, params);
    assert "this is a test, h1:{} h2:melonkid h3:${test1} h4:${} h5: h6:${".equals(finalStr);
  }







}
