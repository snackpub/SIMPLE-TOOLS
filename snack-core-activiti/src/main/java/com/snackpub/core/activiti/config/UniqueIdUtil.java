package com.snackpub.core.activiti.config;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import com.snackpub.core.tools.utils.FileUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UniqueIdUtil {
    private static long adjust = 1L;
    private static long nextId = 0L;
    private static long lastId = -1L;
    private static JdbcTemplate jdbcTemplate;

    private static void init() {
        try {
//      jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplateSN");
//      String path = FileUtil.getClassesPath() + "conf/app.properties".replace("/", File.separator);
//      String strAdjust = FileUtil.readFromProperties(path, "genId.adjust");
//      if (strAdjust != null) {
//        adjust = Integer.parseInt(strAdjust);
//      }
        } catch (Exception ex) {
            adjust = 1L;
        }
    }

    private static void getNextIdBlock() {
        if (jdbcTemplate == null) {
            init();
        }
        Long bound = Long.valueOf(-1L);
        Integer incremental = Integer.valueOf(-1);
        String sql = "SELECT bound,incremental FROM SYS_DB_ID T WHERE T.ID=?";
        String upSql = "UPDATE SYS_DB_ID  SET BOUND=? WHERE ID=?";
        try {
            Map map = jdbcTemplate.queryForMap(sql, new Object[]{Long.valueOf(adjust)});
            bound = Long.valueOf(Long.parseLong(map.get("bound").toString()));
            incremental = Integer.valueOf(Integer.parseInt(map.get("incremental").toString()));
            nextId = bound.longValue();
            lastId = bound.longValue() + incremental.intValue();
            jdbcTemplate.update(upSql, new Object[]{Long.valueOf(lastId), Long.valueOf(adjust)});
        } catch (EmptyResultDataAccessException e) {
            insertNewComputer();
        }
    }

    public static  String getGuid2() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    private static void insertNewComputer() {
        try {
            lastId = 10000L;
            String sql = "INSERT INTO SYS_DB_ID (id,incremental,bound) VALUES(" + adjust + ",10000," + lastId + ")";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized long genId() {
        if (lastId <= nextId) {
            getNextIdBlock();
        }
        long _nextId = nextId++;
        return _nextId + adjust * 10000000000000L;
    }

    public static final String getGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static void main(String[] args)
            throws Exception {
    }
}
