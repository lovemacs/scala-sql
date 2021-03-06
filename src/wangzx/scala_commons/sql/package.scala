package wangzx.scala_commons

import javax.sql.DataSource
import java.sql.Connection
import java.sql.ResultSet
import java.io.InputStream
import java.sql.ResultSetMetaData

package object sql {

  implicit def enhanceConnection(conn: Connection) = new RichConnection(conn)

  implicit def enhanceDataSource(datasource: DataSource) = new RichDataSource(datasource)

  implicit def enhanceStringContext(sc: StringContext) = new SQLStringContext(sc)

  implicit def enhancePlainSql(stmt: String) = SQLWithArgs(stmt, null)

}

package sql {

  case class SQLWithArgs(sql: String, args: Seq[Any])

  class SQLStringContext(sc: StringContext) {
    def sql(args: Any*) = SQLWithArgs(sc.parts.mkString("?"), args)
  }

  /**
   * instead of using reflect mechanism, a bean maybe read field from ResultSet itself
   */
  trait ResultSetConvertable {
    def fromResultSet(rs: ResultSet)
  }

}