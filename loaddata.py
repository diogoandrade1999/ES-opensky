import json

def main():
    res = []
    with open('airports.txt', 'r') as f:
        for index, line in enumerate(f):
            data = line.rstrip().split(';')
            name = data[0]
            city = data[1]
            country = data[2]
            iata = data[3]
            icao = data[4]
            res += [{"name": name, "city": city, "country": country, "iata": iata, "icao": icao}]

    with open('airports.json', 'w') as f:   
        f.write(json.dumps(res))

if __name__ == "__main__":
    main()
