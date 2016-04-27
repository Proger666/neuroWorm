import jneat.Population;

/**
 * Created by Scorpa on 4/27/2016.
 */
import jneat.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class Main {
    public static final int POP_SIZE = 300;
    public static final int NET_INPUT = 555 * 826;
    public static final int NET_OUTPUT = 1 + 1 + 1 + 1;
    public static final int MAX_INDEX_NODES = 5;
    public static final double CON_NODE_CHANSE = 0.5D;

    public static void main(String[] args) throws MalformedURLException {

//
//        Population neatPop = new Population(POP_SIZE /* population size */,
//                NET_INPUT /* network inputs */,
//                NET_OUTPUT /* network outputs */,
//                MAX_INDEX_NODES /* max index of nodes */,
//                true /* recurrent */,
//                CON_NODE_CHANSE /* probability of connecting two nodes */);
        runSimulation(null);
    }

    private static void runSimulation(Population neatPop) throws MalformedURLException {
        connectToChrome();
        //runEvolution(neatPop);
       // setFitness(neatPop);

    }

    private static void connectToChrome() throws MalformedURLException {

        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
        driver.get("http://www.google.com");

        driver.get("http://slither.io/");

        WebElement element = driver.findElement(By.id("nick"));

        element.sendKeys("Scorpa");

      driver.findElement(By.cssSelector("*[class^='btnt nsi sadg1']")).click();

    }

    private static void setFitness(Population neatPop) {
        Vector neatOrgs = neatPop.getOrganisms();
/*
        for(int i=0;i<neatOrgs.size();i++)
        {
            // Assign each organism a "fitness". A measure of how well the organism performed since the last evolution.
            ((Organism)neatOrgs.get(i)).setFitness(current.screen.getFintess());
        }

        neatPop.epoch(generation++); // Evolve the population and increment the generation.
  */  }

    public static void runEvolution(Population neatPop) {
        Vector neatOrgs = neatPop.getOrganisms();
        for (int i = 0; i < neatOrgs.size(); i++) {
            // Extract the neural network from the jNEAT organism.
            Network brain = ((Organism) neatOrgs.get(i)).getNet();

            double inputs[] = new double[NET_INPUT + 1];
            inputs[NET_INPUT] = -1.0; // Bias

            // Populate the rest of "inputs" from this organism's status in the simulation.
            //
            //

            // Load these inputs into the neural network.
            brain.load_sensors(inputs);

            int net_depth = brain.max_depth();
            // first activate from sensor to next layer....
            brain.activate();

            // next activate each layer until the last level is reached
            for (int relax = 0; relax <= net_depth; relax++) {
                brain.activate();
            }

            // Retrieve outputs from the final layer.
            double[] outputs = new double[4];

            for (int j = 0; j < 4; j++) {
                {
                    outputs[j] = ((NNode) brain.getOutputs().elementAt(j)).getActivation();

                }

            }
        }
    }
}
