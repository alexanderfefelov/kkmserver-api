// http://derekwyatt.org/2014/11/14/a-sequential-execution-context/

package com.auvik.util

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Try

/**
  * Queues the operations to run in sequence, regardless of the nature of
  * the underlying threadpool.
  */
class SequentialExecutionContext private(ec: ExecutionContext) extends ExecutionContext {
  // Rather than use a traditional "queue", we're going to use a chain of Futures instead.
  // I'm not 100% happy with this, but it does simplify the example tremendously
  val queue = new AtomicReference[Future[Unit]](Future.successful(()))

  /**
    * The execute method is the goal method of the EC. The implementation ensures that
    * sequentialism is maintained.
    * */
  def execute(runnable: Runnable): Unit = {
    // Normally you would see chaining of Futures using a combinator, such as `flatMap`,
    // but that would just create a memory leak. Every composed Future has a handle to
    // that which it is composed of. To eliminate that, we compose against another
    // Future that we obtain from this promise.
    val p = Promise[Unit]()

    @tailrec
    def add(): Future[_] = {
      val tail = queue.get()

      // Simple replacement of the head of the queue
      if (!queue.compareAndSet(tail, p.future)) {
        add()
      } else {
        tail
      }
    }

    // Here's the sequentialism. The 'current' Future must first complete, and then we
    // will execute that which we were given to do in the first place. The "future"
    // here really does take on the role of a queue
    add().onComplete(_ ⇒ p.complete(Try(runnable.run())))(ec)
  }

  /**
    * yeah, yeah... whatever.
    */
  def reportFailure(cause: Throwable): Unit = ec.reportFailure(cause)
}

object SequentialExecutionContext {
  /**
    * Simple factory method to make construction of the SeqEC simpler
    */
  def apply(ec: ExecutionContext) = new SequentialExecutionContext(ec)
}
