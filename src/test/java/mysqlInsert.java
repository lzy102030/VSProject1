/*
public class mysqlInsert {
    void insert() {
        int step = 50 * 10000;
        Connection conn = ConnectionPool.bds.getConnection();
        conn.setAutoCommit(false);
        String sql = "SET UNIQUE_CHECKS=0";
        Statement st = conn.createStatement();
        st.execute(sql);

        PreparedStatement ps = conn.prepareStatement("insert into henan_enterprise(name) values(?)");
        int count = 0;
        long time0 = System.currentTimeMillis();
        for (String s : ne) {
            ps.setString(1, s);
            ps.addBatch();
            if (++count % step == 0) {
                ps.executeBatch();
                long t = System.currentTimeMillis();
                logger.debug("execute " + count + " times!用时：" + LocalUtil.formatTime(t - time0));
            }
        }
        if (ne.size() % step != 0) {
            ps.executeBatch();
            long t = System.currentTimeMillis();
            logger.debug("execute " + count + " times!用时：" + LocalUtil.formatTime(t - time0));
        }
        conn.commit();
        sql = "SET UNIQUE_CHECKS=1";
        st.execute(sql);
        st.close();
        ps.close();
        conn.close();
    }
}
*/
