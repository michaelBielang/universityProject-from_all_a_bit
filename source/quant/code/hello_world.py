from qiskit import ClassicalRegister, QuantumRegister, QuantumCircuit
from qiskit import execute
from qiskit import IBMQ
from qiskit.tools.jupyter import *
from qiskit.providers.ibmq import least_busy
# import basic plot tools
from qiskit.tools.visualization import plot_histogram, circuit_drawer

IBMQ.load_accounts()

IBMQ.backends()

backend = least_busy(IBMQ.backends(simulator=False))
print("The least busy backend is " + backend.name())


q = QuantumRegister(2)
c = ClassicalRegister(2)
qc = QuantumCircuit(q, c)
qc.h(q[0])
qc.cx(q[0], q[1])
qc.measure(q, c)
job_exp = execute(qc, backend=backend, shots=1024, max_credits=3)

plot_histogram(job_exp.result().get_counts(qc))
print('You have made entanglement!')

qc.draw()
