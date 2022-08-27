package com.n9.repository;

import com.n9.model.TinyUrlData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class BitlyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String RESOURCE_TINY_URL_QRY = "select resourceid,shorturl from resource where shorturl like '%bit.ly%'";


    public List<TinyUrlData>  getTinyUrlInfo() {

        List<TinyUrlData> tinyUrlDataList = jdbcTemplate.query(RESOURCE_TINY_URL_QRY, new RowMapper<TinyUrlData>() {

            @Override
            public TinyUrlData mapRow(ResultSet rs, int rowNum) throws SQLException {
                TinyUrlData tinyUrlData = new TinyUrlData();
                tinyUrlData.setResourceid(rs.getString("resourceid"));
                tinyUrlData.setShorturl(rs.getString("shorturl"));
                return tinyUrlData;
            }
        });

        return tinyUrlDataList;

    }


}
