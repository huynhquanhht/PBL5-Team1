import sys
import matplotlib.pyplot as plt

lines = []
for line in open('graph_data.log'):
    if "avg" in line:
        lines.append(line)

iterations = []
avg_loss = []

print('Retrieving data and plotting training loss graph...')
for i in range(len(lines)):
    lineParts = lines[i].split(',')
    iterations.append(int(lineParts[0].split(':')[0]))
    if float(lineParts[1].split()[0]) > 10:
        avg_loss.append(1)
    else:
        avg_loss.append(float(lineParts[1].split()[0]))

fig = plt.figure()
for i in range(0, len(lines)):
    if avg_loss[i:i + 2] == 1:
        plt.plot(iterations[i:i + 2], avg_loss[i:i + 2],'w.', markersize=0.9)
    else:
        plt.plot(iterations[i:i + 2], avg_loss[i:i + 2], 'r.', markersize=0.9)

plt.xlabel('Batch Number')
plt.ylabel('Avg Loss')
fig.savefig('training_loss_plot.png', dpi=1000)

print('Done! Plot saved as training_loss_plot.png')