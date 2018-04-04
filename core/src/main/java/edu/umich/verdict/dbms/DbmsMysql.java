package edu.umich.verdict.dbms;


import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.google.common.base.Joiner;

import edu.umich.verdict.VerdictContext;
import edu.umich.verdict.datatypes.SampleParam;
import edu.umich.verdict.datatypes.TableUniqueName;
import edu.umich.verdict.exceptions.VerdictException;
import edu.umich.verdict.util.StringManipulations;

public class DbmsMysql extends DbmsJDBC {

    public DbmsMysql(VerdictContext vc, String dbName, String host, String port, String schema, String user,
                    String password, String jdbcClassName) throws VerdictException {
        super(vc, dbName, host, port, schema, user, password, jdbcClassName);
    }

    @Override
    public String getQuoteString() {
        return "'";
    }

    @Override
    protected String randomPartitionColumn() {
        int pcount = partitionCount();
        return String.format("MOD(ROUND(RAND(UNIX_TIMESTAMP())*%d), %d) AS %s",
                pcount, pcount, partitionColumnName());
    }

    @Override
    protected String randomNumberExpression(SampleParam param) {
        String expr = "RAND(UNIX_TIMESTAMP())";
        return expr;
    }

    @Override
    protected String modOfRand(int mod) {
        return String.format("mod(abs(rand(unix_timestamp())), %d)", mod);
    }

    @Override
    public String modOfHash(String col, int mod) {
        return String.format("crc32(cast(%s%s%s as string)) %% %d", getQuoteString(), col, getQuoteString(), mod);
    }

    @Override
    public String modOfHash(List<String> columns, int mod) {
        String concatStr = "";
        for (int i = 0; i < columns.size(); ++i) {
            String col = columns.get(i);
            String castStr = String.format("cast(%s%s%s as string)", getQuoteString(), col, getQuoteString());
            if (i < columns.size() - 1) {
                castStr += ",";
            }
            concatStr += castStr;
        }
        return String.format("mod(crc32(concat_ws('%s', %s)), %d)", HASH_DELIM, concatStr, mod);
    }

    @Override
    protected String randomNumberExpression(SampleParam param) {
        String expr = "rand(unix_timestamp())";
        return expr;
    }

    @Override
    protected String randomPartitionColumn() {
        int pcount = partitionCount();
        return String.format("mod(round(rand(unix_timestamp())*%d), %d) AS %s", pcount, pcount, partitionColumnName());
    }

    @Override
    public Dataset<Row> getDataset() {
        // TODO Auto-generated method stub
        return null;
    }

}


