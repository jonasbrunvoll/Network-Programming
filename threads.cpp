//
//  main.cpp
//  ovint1_Primtall
//
//  Created by Jonas Larsson on 04/02/2020.
//  Copyright © 2020 Jonas Larsson. All rights reserved.
//


#include <iostream>
#include <vector>
#include <thread>

using namespace std;

class PrimeChecker{

private:
    
    /** Methode to check if number is prime */
    bool isPrime(int number){
         if (number < 2) return false;
         if (number < 4) return true;
         if (number % 2 == 0) return false;
         for (int i = 5; i < number - 1; i++) {
             if (number % i == 0) {
                 return false;
             }
         }
        return true;
    };
    
public:
    
    /**
        My own prime number finder.
     */
    vector<int> listePrimes2(int start, int stop, int amountOfThreads){
        mutex lock;
        vector<thread> threads;
        vector<int> primes;
        
        for (int i = 0; i < amountOfThreads; i++) {
            threads.emplace_back(([i, &start, &stop, &lock, &primes, &amountOfThreads, this] {
                
                for (int j =  start + i; j < stop; j+= amountOfThreads) {
                    if (isPrime(j)) {
                        lock.lock();
                        primes.emplace_back(j);
                        
                        cout << "Thread nr. " << (i + 1) <<" found primenumber: " << j <<"." << endl;
                        cout << endl;
                        lock.unlock();
                    }
                }
            }));
        }
        
        for(auto &thread : threads){
               thread.join();
           }

        sort(primes.begin(), primes.end());
        
        cout << "Found " << primes.size() << " prime numbers:" << endl;
        cout << endl;
        for (int i = 0; i < primes.size(); i++) {
            cout <<"Primtall: " << primes[i] << endl;
        }
        cout << endl;
        
        
        return primes;
    }
};


int main() {
    
    int start = 1;
    int end = 100;
    int threads = 5;
    
    PrimeChecker primeChecher;
    
    vector<int> primes = primeChecher.listePrimes2(start, end,threads);
    
}

