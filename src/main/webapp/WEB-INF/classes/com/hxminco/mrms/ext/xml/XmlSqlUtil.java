package com.hxminco.mrms.ext.xml;

import com.hxminco.mrms.comm.entry.xml.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by wandering on 2016/12/28.
 */
public class XmlSqlUtil {
    public static final String MATCH_EQ="EQ";                       //等于
    public static final String MATCH_NE="NE";                       //不等于
    public static final String MATCH_LIKE="LIKE";                   //相似
    public static final String MATCH_START_LIKE="START_LIKE";       //头相似
    public static final String MATCH_END_LIKE="END_LIKE";           //尾相似
    public static final String MATCH_GE="GE";                       //大于等于(>=)
    public static final String MATCH_GT="GT";                       //大于(>)
    public static final String MATCH_LE="LE";                       //小于等于(<=)
    public static final String MATCH_LT="LT";                       //小于(<)


    public static String covertFilter2Condition(Filter filter){
        if(filter == null){
            return "";
        }
        StringBuilder param = new StringBuilder();
        String match = filter.getMatch();
        String field = filter.getField();
        String value = filter.getValue();
        if (MATCH_EQ.equals(match)){
            param.append("and " + propertyToField(field) + "='" + field + "'");
        }else if(MATCH_NE.equals(match)){
            param.append("and " + propertyToField(field) + "<>'" + field + "'");
        }else if(MATCH_LIKE.equals(match)){
            param.append("and " + propertyToField(field) + "like '%" + field + "%'");
        }else if(MATCH_START_LIKE.equals(match)){
            param.append("and " + propertyToField(field) + "like '" + field + "%'");
        }else if(MATCH_END_LIKE.equals(match)){
            param.append("and " + propertyToField(field) + "like '%" + field + "'");
        }else if(MATCH_GE.equals(match)){
            param.append("and " + propertyToField(field) + ">='" + field + "'");
        }else if(MATCH_GT.equals(match)){
            param.append("and " + propertyToField(field) + ">'" + field + "'");
        }else if(MATCH_LE.equals(match)){
            param.append("and " + propertyToField(field) + "<='" + field + "'");
        }else if(MATCH_LT.equals(match)){
            param.append("and " + propertyToField(field) + ">'" + field + "'");
        }else {
            param.append("");
        }
        return param.toString();
    }


    /**
     * 对象属性转换为字段  例如：userName to user_name
     * @param property 字段名
     * @return
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字段转换成对象属性 例如：user_name to userName
     * @param field
     * @return
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }

    /**
     * 获取xml里的相关参数
     * @return 返回装有参数的map
     */
    public static Map<String, Object> getXmlParams(PageQueryRoot pageQueryRoot,String xml){
        //获取开始条数
        String start = pageQueryRoot.getPageQuery().getStart();
        //获取限制数
        String limit = pageQueryRoot.getPageQuery().getLimit();
        //获取参数
        Map<String,Object> params = pageQueryRoot.getAllFilters();
        //获取返回字段
        List<String> returns = pageQueryRoot.getAllReturns();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("params", params);
        map.put("returns", returns);
        return map;
    }

    /**
     * 返回查询结果的xml
     * @param pageQueryRoot
     * @param map 参数Map
     * @param list 查询的结果集
     * @return 结果xml
     */
    public static String getReturnData(PageQueryRoot pageQueryRoot,Map<String, Object> map,List list){
        PageDataRoot pageDataRoot = new PageDataRoot(Integer.parseInt((String)map.get("start")),Integer.parseInt((String)map.get("limit")),new Long((Long)map.get("count")).intValue());
        List<Record> recordList = new ArrayList<Record>();
        //构造返回数据
        for(Object o : list){
            recordList.add(pageDataRoot.makeRecord((List<String>)map.get("returns"),o));
        }
        //设置返回数据
        pageDataRoot.getPageData().setRecords(recordList);
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.processAnnotations(PageDataRoot.class);
        return xstream.toXML(pageDataRoot);
    }

    public static List<Filter> makeFilters(Map<String,String> map){
        if(map == null || map.size()==0){
            return null;
        }
        List<Filter> filters = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> one:entries){
            Filter filter = new Filter();
            filter.setField(one.getKey());
            filter.setValue(one.getValue());
            filters.add(filter);
        }
        return filters;
    }

    public static List<Return> makeReturns(List<String> list){
        if(list == null || list.size()==0){
            return null;
        }
        List<Return> returns = new ArrayList<>();
        for (String one:list){
            Return ret = new Return();
            ret.setField(one);
            returns.add(ret);
        }
        return returns;
    }

}
