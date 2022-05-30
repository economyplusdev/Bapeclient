x86_64-w64-mingw32-g++ -Wall -DBUILD_DLL -O2 -O2 -std=c++11 -m64  -c main_small.cpp -o main.o
x86_64-w64-mingw32-g++ -shared -Wl,--dll main.o -o $2 output/bape.dll -m64 -static -static-libgcc -static-libstdc++ -luser32
read -s -n 1 -p "Press any key to continue . . ."