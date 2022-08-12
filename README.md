# ProducerConsumer
How to use: Run the .java file, the program should run, and the threads should start printing lines to the screen 
until one of them reach the limit and then stop the program.

Description: This program’s objective is to have producer and consumer threads fill and empty an array. 
Three cases I had to achieve was having the producer waiting after filling the buffer, the consumer waiting with the buffer empty, and the buffer being partially full. I made use of wait() and notify() to keep the threads synchronized. 
I also used the random method so that there was a chance that the threads swapped control of the buffer without filling the buffer completely.
The random method is also used for the limit of each thread, the limit variable decides when one thread will finish doing it’s job causing the other to finish up and close the program so that the program doesn’t endlessly loop.

Observations: Sometimes when you run the program the threads have different counts which could make you think they are not synchronized. 
It is due to the randomness, one of the thread's turns could be skipped. Which could lead to one thread reaching it’s limit while the other thread’s counter is less than the limit. I also seen the opposite where a thread count is way over the limit of the other thread.
