from pprint import pprint

content = ""

def readFile(filename):
    with open(filename) as f:
        content = f.readlines()
    pprint(content)

def parse():
    