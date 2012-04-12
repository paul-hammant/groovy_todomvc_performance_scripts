@Grapes([
 @Grab("org.seleniumhq.selenium:selenium-java:2.20.0"),
 @Grab("org.seleniumhq.selenium.fluent:fluent-selenium:1.6.3"), 
 @Grab("org.hamcrest:hamcrest-all:1.1"),
 @GrabExclude('xml-apis:xml-apis')
])

import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.Keys
import org.openqa.selenium.StaleElementReferenceException
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.seleniumhq.selenium.fluent.FluentBy.attribute
import static org.hamcrest.Matchers.containsString;
import static org.seleniumhq.selenium.fluent.FluentBy.last
import static org.seleniumhq.selenium.fluent.Period.secs


def driver = new FirefoxDriver()
def fluent = new FluentWebDriverImpl(driver)

driver.get "http://paul-hammant.github.com/backbone_todo_app"

def todoField = fluent.input(attribute("id", "new-todo"))

for ( i in 1..1000 ) {
  def todo = "qwerty " + i
  def start = System.currentTimeMillis()
  // add an entry
  todoField.sendKeys todo + Keys.RETURN  	
  def liStart = System.currentTimeMillis()
  def li = driver.findElement(By.xpath("//ul/li[position() = last()]"))
  def liDur = System.currentTimeMillis() - liStart
  def isTodoStart = System.currentTimeMillis()
  assertThat(li.getText(), containsString(todo))
  def isTodoDur = System.currentTimeMillis() - isTodoStart
  li.findElement(By.tagName("input")).click()
  println "" + i + "," + (System.currentTimeMillis() - start) + "," + liDur + "," + isTodoDur
}

driver.close()