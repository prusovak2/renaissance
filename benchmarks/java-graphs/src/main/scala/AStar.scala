package org.renaissance.astar

import org.renaissance.Benchmark
import org.renaissance.Benchmark._
import org.renaissance.BenchmarkContext
import org.renaissance.BenchmarkResult
import org.renaissance.BenchmarkResult.Validators
import org.renaissance.License

@Name("a-star")
@Group("java-graphs")
@Summary("Runs the A* algorithm.")
@Licenses(Array(License.MIT))
@Repetitions(30)
@Parameter(name = "abraka", defaultValue = "dabra")
@Configuration(name = "test")
@Configuration(name = "jmh")
final class AStar extends Benchmark {

  //private var benchmark: JavaKMeans = _
  private var abraka: String = _

  override def setUpBeforeAll(c: BenchmarkContext): Unit = {
    abraka = c.parameter("abraka").toString
    println("Astar executing setUpBeforeAll")
    println(abraka);
    //benchmark = new JavaKMeans(DIMENSION, threadCountParam)
    //data = JavaKMeans.generateData(vectorLengthParam, DIMENSION, CLUSTER_COUNT)
    ()
  }

  override def run(c: BenchmarkContext): BenchmarkResult = {
    /*val results = for (_ <- 0 until LOOP_COUNT) yield {
      benchmark.run(CLUSTER_COUNT, data, ITERATION_COUNT)
    }*/
    println("Astar executing RUN")
    // TODO: add proper validation of the individual sub-benchmarks
    Validators.dummy(42)
  }

  override def tearDownAfterAll(c: BenchmarkContext): Unit = {
    println("Astar executing TEARDOWN, after all...")
    //benchmark.tearDown()
    //benchmark = null
    //data = null
    ()
  }
}
