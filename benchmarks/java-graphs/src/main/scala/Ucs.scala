package org.renaissance.graphs;

import Benchmarks.JavaUcsBenchmark
import org.renaissance.Benchmark
import org.renaissance.Benchmark._
import org.renaissance.BenchmarkContext
import org.renaissance.BenchmarkResult
import org.renaissance.BenchmarkResult.Validators
import org.renaissance.License
import java.util.NoSuchElementException

@Name("ucs")
@Group("java-graphs")
@Summary("Runs the uniform cost search algorithm.")
@Licenses(Array(License.MIT))
@Repetitions(30)
@Parameter(name = "numNodes", defaultValue = "1000000")
@Parameter(name = "numEdges", defaultValue = "1500000")
@Parameter(name = "nodePayloadSize", defaultValue = "1000")
@Parameter(name = "numTargets", defaultValue = "1")
@Parameter(name = "seed")
final class Ucs extends Benchmark {

  private var benchmark: JavaUcsBenchmark = new JavaUcsBenchmark()

  override def setUpBeforeAll(c: BenchmarkContext): Unit = {
    val numNodes = c.parameter("numNodes").toPositiveInteger
    val numEdges = c.parameter("numEdges").toPositiveInteger
    val nodePayloadSize = c.parameter("nodePayloadSize").toInteger.asInstanceOf[Short]
    val numTargets = c.parameter("numTargets").toPositiveInteger
    var seed = 0;
    var hasSeed = false;
    if (c.parameter("seed").value().isEmpty()) {
      hasSeed = false
    } else {
      hasSeed = true;
      seed = c.parameter("seed").toInteger
    }
    println(
      s"Initializing UCS with $numNodes nodes, $numEdges edges, $numTargets targets, payload size $nodePayloadSize and seed ${if (hasSeed) seed else null}"
    );
    benchmark.generateProblemRandomly(
      numNodes,
      numEdges,
      nodePayloadSize,
      numTargets,
      if (hasSeed) seed else null
    );
  }

  override def run(c: BenchmarkContext): BenchmarkResult = {
    var result = benchmark.run()
    Validators.dummy(result)
  }
}
