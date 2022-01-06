import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection

object Payroll {

    def main(args: Array[String]):Unit={
      
     var scanner=new Scanner(System.in)
    println("Please eneter your Full Name(A-z)")
       var name=scanner.nextLine()
    println("Please eneter your ID (1-9999)")
       var ID=scanner.nextInt()
     println("Please eneter your total Weekly hours")   
        var hoursWorked= scanner.nextInt()
         scanner.nextLine()
     println("Please enter S for Salaried and H for Hourly") 
       var typeEmployment = scanner.nextLine()   
       //hourly pay rate
       var  rate= 12.50
       var paymentAmount=0;

        $typeEmployment.toString
       if($typeEmployment=="h"){

        if($hoursWorked<=40) 
        paymentAmount=$hoursWorked*rate
       return "Weekly paycheck is $" +paymentAmount
        else  
        paymentAmount=(40*rate + ($hoursWorked-40)*rate*1.5)  
       return "Weekly paycheck is $" +paymentAmount

       }
   else "This is a Salaried Employee"    
    }
       println(earnings("s", 0))

    

        // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/payroll" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "4370335s" // Update to include your password

    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      /*This create the Employee table for Payroll Database*/
      val creat_Table = statement.executeUpdate("Create table Employee(EmployeeID Int, fullName varchar(255), Employment_Type varchar(255),  "+
                                               " hoursWorked Decimal, overTime Decimal, Hourly_Rate Decimal,Payment Decimal);") // Change query to your table"  

      val resultSet=statement.executeQuery("INSERT INTO employee(EmployeeID, fullName,Employment_Type, hoursWorked,overTime,Payment) Values()")                                         
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