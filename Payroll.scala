import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection

object Payroll {

    def main(args: Array[String]):Unit={
     
     println("Please eneter your total Weekly hours")   
        var scanner=new Scanner(System.in)
        var hoursWorked= scanner.nextInt()
         scanner.nextLine()
     println("Please enter S for Salaried and H for Hourly") 
       var typeEmployment = scanner.nextLine()   
       //hourly pay rate
       var  rate= 12.50

    //function calculates hours work by employee
    def earnings($typeEmployment:String, $hoursWorked:Double): String={
        $typeEmployment.toString
       if($typeEmployment=="h"){

        if($hoursWorked<=40) "Weekly paycheck is $" +$hoursWorked*rate

        else "Weekly paycheck is $" +(40*rate + ($hoursWorked-40)*rate*1.5)

       }
   else "This is a Salaried Employee"    
    }
       println(earnings("s", 0))



        // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/sqlexamples" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "########" // Update to include your password

    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM orders") // Change query to your table
      while ( resultSet.next() ) {
        print(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3))
        println()
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
    }
   
}
  