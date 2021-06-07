# This is a sample Python script.
from ast import parse
from datetime import datetime
# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    date_str = datetime.now()
    date = date_str.strftime("%H:%M:%S %d:%m:%y")
    print(date)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
