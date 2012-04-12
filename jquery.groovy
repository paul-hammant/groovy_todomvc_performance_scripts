@Grapes([
 @Grab("org.seleniumhq.selenium:selenium-java:2.20.0"),
 @Grab("org.hamcrest:hamcrest-all:1.1"),
 @GrabExclude('xml-apis:xml-apis')
])

import java.util.concurrent.TimeUnit
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.Keys
import org.openqa.selenium.StaleElementReferenceException
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.Matchers.containsString
import static org.junit.Assert.assertThat

def driver = new FirefoxDriver()

driver.get "http://addyosmani.github.com/todomvc/architecture-examples/jquery/"

def todoField = driver.findElement(By.id("new-todo"))

for ( i in 1..1000 ) {
  def todo = "qwerty " + i
  def start = System.currentTimeMillis()
  // add an entry
  todoField.sendKeys todo + Keys.RETURN  	
  def liStart = System.currentTimeMillis()
  def li = driver.findElement(By.xpath("//li[@data-id != '123' and position() = last()]"))
  def liDur = System.currentTimeMillis() - liStart
  def isTodoStart = System.currentTimeMillis()  
  assertThat(li.getText(), containsString(todo))
  def isTodoDur = System.currentTimeMillis() - isTodoStart
  li.findElement(By.tagName("input")).click()
  println "" + i + "," + (System.currentTimeMillis() - start) + "," + liDur + "," + isTodoDur
}

driver.close()


