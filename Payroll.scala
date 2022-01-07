import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection

object Payroll {

    def main(args: Array[String]):Unit={
        
     var scanner=new Scanner(System.in)
       //hourly pay rate
       var  rate= 12.50
       var paymentAmount:Double=0;
       var typeEmployment="Hourly" 
     

        // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/Payroll" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "4370335s" // Update to include your password

    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
   
      // create the statement, and run the select query
     val statement = connection.createStatement()
      /*This create the Employee table for Payroll Database 
      val creat_Table = statement.executeUpdate("Create table Employee(EmployeeID serial, fName varchar(255),lName varchar(255), Employment_Type varchar(255),  "+
                                              " hoursWorked Decimal (10,2), Hourly_Rate Decimal(10,2),Date_Time datetime default current_timestamp on Update current_timestamp);") 
                                              */
       
          println("Please eneter your LName(A-z)")
          var fName=scanner.nextLine()                                      
         println("Please eneter your LName(A-z)")
         var lName=scanner.nextLine() 
         println("Please eneter your total Weekly hours")   
         var hoursWorked= scanner.nextInt()
          scanner.nextLine()
         println("Please enter S for Salaried and H for Hourly") 
         var typeEmployment = scanner.nextLine()
         if((typeEmployment=="S")&&(typeEmployment=="s")){
            typeEmployment="Salaried"
         } else if((typeEmployment=="h") && (hoursWorked<=40)) {
              paymentAmount=hoursWorked*rate
              typeEmployment="hourly"
               
         }else{
           paymentAmount=(40*rate + (hoursWorked-40)*rate*1.5)
         }  
         val query="INSERT INTO employee(fName, lName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+fName+"','"+lName+"','"+typeEmployment+"'," +
                                                         ""+hoursWorked+","+rate+");"
        //println(query)
             val resultSet=statement.executeUpdate(query)

       val creat_view = statement.executeUpdate("create view  Paycheck AS select EmployeeID, concat(fname , lname) as FullName, Employment_Type,"+
                                                 "Hourly_Rate,hoursWorked,(hoursWorked-40) as Overtime, (Hourly_Rate*hoursWorked ) as TotalPay from Employee;" ) 
                                                                                     
       
        //val resultDb = statement.executeQuery("SELECT * FROM employee;")
        val resultDbView = statement.executeQuery("SELECT * FROM Paycheck;")                                                                        
      while ( resultDbView.next())
        print(resultDbView.getString(1) + " " + resultDbView.getString(2) + " " + resultDbView.getString(3))
        println()
      }

     catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
    }
  
    

}