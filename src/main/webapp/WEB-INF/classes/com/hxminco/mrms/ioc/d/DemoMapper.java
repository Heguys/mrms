package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.Demo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DemoMapper {

    @Select({"SELECT * FROM demo"})
    @Results(value = {
            @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    List<Demo> getDemoList();

    @Select({"INSERT INTO demo(id, name) VALUES ( #{id, jdbcType=NUMERIC}, #{name, jdbcType=VARCHAR} )"})
    void insertDemo(Demo demo);

    @Select({"DELETE FROM demo WHERE id = #{0, jdbcType=NUMERIC}"})
    void deleteDemo(int id);
    
    List<Demo> getDemoListXml();

}
