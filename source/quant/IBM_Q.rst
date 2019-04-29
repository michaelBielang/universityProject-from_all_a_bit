IBM Q
=====

Quantum Composer:
'''''''''''''''''
is a graphical user interface where you can create your own quantum circuit (which we call a quantum score).

Python-SDK "Qiskit"
'''''''''''''''''''
- Quantum Information Science Toolkit
- Open Source Project

Install Qiskit
'''''''''''''''
https://qiskit.org/documentation/install.html


Qiskit get started:
'''''''''''''''''''
https://www.youtube.com/watch?v=V3hXSftZuoc


Terminal
'''''''''
``export PATH=~/anaconda3/bin:$PATH``
``conda create -q quantum_environment python=3``
``source activate quantum_environment``
``jupyter console``


Tutorial
'''''''''
https://qiskit.org/documentation/getting_started.html

qiskit-test.py
..............
-> returns statevector for the quantum circuit:
``[0.707+0.j 0.   +0.j 0.   +0.j 0.   +0.j 0.   +0.j 0.   +0.j 0.   +0.j
 0.707+0.j]``

qiskit-test2.py
................
-> calculates the 2^n x 2^n matrix representing the gates in the quantum circuit
``[[ 0.707+0.j  0.707+0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j
   0.   +0.j  0 .   +0.j]
 [ 0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j
   0.707+0.j -0.707+0.j]
 [ 0.   +0.j  0.   +0.j  0.707+0.j  0.707+0.j  0.   +0.j  0.   +0.j
   0.   +0.j  0.   +0.j]
 [ 0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.707+0.j -0.707+0.j
   0.   +0.j  0.   +0.j]
 [ 0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.707+0.j  0.707+0.j
   0.   +0.j  0.   +0.j]
 [ 0.   +0.j  0.   +0.j  0.707+0.j -0.707+0.j  0.   +0.j  0.   +0.j
   0.   +0.j  0.   +0.j]
 [ 0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j
   0.707+0.j  0.707+0.j]
 [ 0.707+0.j -0.707+0.j  0.   +0.j  0.   +0.j  0.   +0.j  0.   +0.j
   0.   +0.j  0.   +0.j]]``

qiskit-test3.py
................
-> get_counts(circuit) gives the aggregated binary outcomes of the circuit you submitted.
``{'000': 526, '111': 498}``

qiskit_IBMQ.py
...............
-> avaiable Backends = list of devices avaiable to you
``[<IBMQBackend('ibmqx4') from IBMQ()>,
 <IBMQBackend('ibmq_16_melbourne') from IBMQ()>,
 <IBMQBackend('ibmq_qasm_simulator') from IBMQ()>,
 <IBMQBackend('ibmq_20_tokyo') from IBMQ(ibm-q-internal, research, yorktown)>]``


Hello World
............
https://nbviewer.jupyter.org/github/Qiskit/qiskit-tutorial/blob/master/community/hello_world/quantum_world.ipynb






