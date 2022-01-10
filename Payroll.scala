import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection
import java.io._
import javax.crypto.BadPaddingException

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
     /*This is ran once to create the Employee table for Payroll Database 
     val creat_Table = statement.executeUpdate("Create table Employee(EmployeeID serial, firstName varchar(255),lastName varchar(255), Employment_Type varchar(255),  "+
                                             " hoursWorked Decimal (10,2), Hourly_Rate Decimal(10,2),Date_Time datetime default current_timestamp on Update current_timestamp);") 
                                            */
      /*This is ran once to create the view in database*/  
    /** val creat_view = statement.executeUpdate("create view  Paycheck AS select EmployeeID, concat(firstName ,' ', lastName) as FullName, Employment_Type,"+
                                               " Hourly_Rate,hoursWorked,(hoursWorked-40) as Overtime, (Hourly_Rate*hoursWorked ) as TotalPay from Employee where EmployeeID =(SELECT max(EmployeeID) FROM Employee);") 
                                                                                                     
                                   **/
        
            println("Please eneter your First Name(A-Z)")
               var firstName=scanner.nextLine() 
        
           println("Please eneter your Last Name(A-Z)")
          var  lastName=scanner.nextLine()
            
            println("Please eneter your total Weekly hours")   
            var  hoursWorked= scanner.nextInt()
            
          if(hoursWorked<=40) {
           paymentAmount=hoursWorked*rate
              
          }
       
          while (hoursWorked>=60){
            println("Company does not allow working more than 60hrs be week")
            println("Please eneter your total Weekly hours") 
            hoursWorked= scanner.nextInt()
            if((hoursWorked < 60) && (hoursWorked > 0) )
             hoursWorked;
          }
          
          
          /** Overtime calculation **/
          paymentAmount=(40*rate + (hoursWorked-40)*rate*1.5)
          
          
         println(" Is this info correct? enter Y/N")
         println("FullName :" +firstName+" "+lastName+ " :Hours Worked:" +hoursWorked)
         println("========================================================================================================")
          var verification=scanner.next().toString.toUpperCase()
          val query="INSERT INTO employee(firstName, lastName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+firstName+"','" +lastName+"','"+typeEmployment+"'," +
                                                     ""+hoursWorked+","+rate+");" 
       // println(query)
        val resultSet1=statement.executeUpdate(query)

          while (verification =="N"){
            val resultSet2=statement.executeUpdate("delete from employee order by EmployeeID desc limit 1;")
               println("Please try again")
               println("Please eneter your First Name(A-Z)")
               var firstName=scanner.next() 
             println("Please eneter your Last Name(A-Z)")
            var  lastName=scanner.next()
            
            println("Please eneter your total Weekly hours")   
            var  hoursWorked= scanner.nextInt()   
            println(" Is this info correct? enter Y/N")
          println("FullName :" +firstName+" "+lastName+ " :Hours Worked:" +hoursWorked)
           println("========================================================================================================")
          val query="INSERT INTO employee(firstName, lastName,Employment_Type, hoursWorked,Hourly_Rate) Values ('"+firstName+"','" +lastName+"','"+typeEmployment+"'," +
                                                     ""+hoursWorked+","+rate+");" 

            val resultSet1=statement.executeUpdate(query)
               verification=scanner.next().toString.toUpperCase() 
        }
         /** Printing the headings**/
       println("=============================================================================================================================================")                    
       println("Employee_ID "+ "\t" +"FullName"+ "\t "  +"EmployeeType "+ "\t\t"  +"Hourly_Rate "+ "\t\t"  +"HoursWorked "+ "\t\t" +"Overtime "+ "\t\t"   +"TotalPay ")
       println("=============================================================================================================================================")
        //val resultDb = statement.executeQuery("SELECT * FROM employee;")
        val resultDbView = statement.executeQuery("SELECT * FROM Paycheck;")
                                                                                 
      while ( resultDbView.next())
        
        println(resultDbView.getString(1) + "\t\t " + resultDbView.getString(2) + " \t\t  " + resultDbView.getString(3)+ " \t\t  "+resultDbView.getString(4)
        + " \t\t  "+resultDbView.getString(5)+ " \t\t  "+resultDbView.getString(6)+ " \t\t\t "+resultDbView.getString(7)) 
        println()
      }
    
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()
}

}