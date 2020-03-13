//
//  main.cpp
//  oving2
//
//  Created by Jonas Larsson on 17/02/2020.
//  Copyright © 2020 Jonas Larsson. All rights reserved.
//
/**
 CONDITION VARIABLES
    - A condition variable is an object able to block the calling thread until notified to resume.
    - A conditional variable uses unique_lock, to lock the thread when one of the waiting functions are called.
 */




 #include <mutex>
 #include <iostream>
 #include <vector>
 #include <thread>
 #include <functional>
 #include <list>
 #include <condition_variable>



 using namespace std;

 class Workers{
 private:
     mutex taskLock;
     list <function<void()>>tasks;
     int numberOfThreads;
     vector<thread> worker_threads;
     condition_variable cv;
     bool stopThreads = false;
     
     
 public:
     
     //Constructor. Takes in number of threads to be created.
     Workers(int threads){
         numberOfThreads = threads;
     };
     
     
     // Fills up the list 'tasks' with executable tasks
     void post(function<void()> task){
         lock_guard<mutex> lock(taskLock); //Locks and unlocks tasklist.
         tasks.emplace_back(task);         //Adds the new task at the back of the queue.
         cv.notify_all();                  //Unblocks all waiting threads.
     }
     
     
     // Signals to the threads that it is time to stopp and joins the threads.
     void stop(){
         stopThreads = true;
         cv.notify_all();
         
         //Join all the threads after execution is done.
         for (auto &thread : worker_threads)
         thread.join();
     }
     
     // Solves the tasks from the list 'task':
     void start(){
         for (int i = 0; i < numberOfThreads; i++) {
             worker_threads.emplace_back([this]{
                 
                 while (true) {
                     function<void()> task;
                     {
                         
                         unique_lock<mutex> lock(taskLock);
                         while (!stopThreads && tasks.empty()) {
                             cv.wait(lock);
                         }

                         if (!tasks.empty()) {
                             task = *tasks.begin(); //Moves task upfront in the list 'tasks'
                             tasks.pop_front();     //Removes task form tasks.
                         } else {
                             return;
                         }
                     }
                     
                     // Execute task
                     if (task) {
                         post_timeout(500);
                         task();
                         
                     }
                     
                 }
             });
         }
     };
     void post_timeout(int milliseconds){
         std::this_thread::sleep_for(std::chrono::milliseconds(milliseconds));
     };
 };





 int main() {
     
     Workers workers_thread(4);
     Workers event_loop(1);
              
     workers_thread.post([]{
         
         cout << " wt: Jeg " << endl;
     });
     
     workers_thread.post([]{
         
         cout << " wt: er " << endl;
     });
     
     workers_thread.post([]{
     
         cout << " wt: en " << endl;
     });
     
     workers_thread.post([]{
     
         cout << " wt: fersken " << endl;
     });
     event_loop.post([]{
     
         cout << "el: hej " << endl;
     });
     event_loop.post([]{
     
         cout << "el: alla " << endl;
     });
     event_loop.post([]{
     
         cout << "el: ihop " << endl;
     });
     
     
     event_loop.start();
     workers_thread.start();
     
     event_loop.stop();
     workers_thread.stop();
     
    
 }
