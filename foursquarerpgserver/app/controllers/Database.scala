package controllers
import play.api._
import play.api.Play.current
import play.api.db._
import play.api.mvc._
import hibernate.UserDto

object Database {
	val sessionFactory = DB.getDataSource();
	println(sessionFactory.getConnection().prepareStatement("SELECT * FROM user").executeQuery().next());
	def f() {}
	def addUser(username:String, token: String): UserDto = {
			val conn = sessionFactory.getConnection();
			val statement = conn.prepareStatement("INSERT INTO `foursquarerpg`.`user` " +
					"(`username`, " +
					"`token`, " +
					"`hp`, "+ 
					"`attack`, " +
					"`defense`, " +
					"`stamina`, " +
					"`exp`, " +
					"`gold`) " +
					"VALUES " +
					"('" + username + "', " +
					"'" + token + "', " +
					"10, " +
					"10, " +
					"10, " +
					"10, " +
					"10, " +
					"10);");
			statement.execute();
			getUser(getMaxUser);
	}
	def getMaxUser(): Int = {
	  val conn = sessionFactory.getConnection();
	  val statement = conn.prepareStatement("select max(id) from user u");
	  val rs = statement.executeQuery();
	  if(rs.next()) {
	    rs.getInt("max(id)");
	  }
	  else -1
	}
	def getUser(id: Int): UserDto = {
			val conn = sessionFactory.getConnection();
			val statement = conn.prepareStatement("SELECT * FROM user u WHERE u.id = ?");
			statement.setInt(1, id);
			val rs = statement.executeQuery();
			val user = new UserDto();
			while(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setToken(rs.getString("token"));
				user.setUsername(rs.getString("username"));
				user.setAttack(rs.getInt("attack"));
				user.setDefense(rs.getInt("defense"));
				user.setHp(rs.getInt("hp"));
				user.setStamina(rs.getInt("stamina"));
				user.setGold(rs.getInt("gold"));
				user.setExperience(rs.getInt("exp"));
			}
			user
	}
}