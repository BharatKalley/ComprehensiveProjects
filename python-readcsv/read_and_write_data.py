import csv

class ReadAndWriteData:
    def __init__(self, filename):
        self.filename = filename

    def read_csv(self):
        data = []
        with open(self.filename, mode='r') as file:
            reader = csv.DictReader(file)
            for row in reader:
                data.append(row)
        return data

if __name__ == "__main__":
    reader = ReadAndWriteData("skills.csv")
    data = reader.read_csv()
    for row in data:
        print(row)
