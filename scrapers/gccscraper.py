from bs4 import BeautifulSoup
import pandas
import requests
import json
import datetime as dt

new_URL = "https://georgiarcc.org/?sort=updated_at&direction=desc&page="
df = pandas.DataFrame()
for i in range(1,6):
    page = requests.get(new_URL+str(i))
    soup = BeautifulSoup(page.content, 'lxml')
    table = soup.find_all('table')[0]
    dfs = pandas.read_html(str(table))
    df = df.append(dfs[0])
df = df.reset_index()
df = df.drop(columns=['index'])
df['Nedocs'] = df['Nedocs'].str.split(" ").str[0]

seen_divs = []

for i, row in df.iterrows():
    old_stat = df.loc[i, 'Status'].split("  ")
    x = 0
    new_stat = []
    while x < len(old_stat):
        new_stat.append(old_stat[x])
        if old_stat[x] not in seen_divs:
            seen_divs.append(old_stat[x])
        x += 2
    df.loc[i, 'Status'] = new_stat

with open('diversions.json', 'w') as outfile:
    json.dump(seen_divs, outfile)

df = df.drop(columns=['County'])

df['Updated'] = pandas.to_datetime(df['Updated'])
df['Updated'] = df['Updated'].dt.strftime('%Y-%m-%d %H:%M:%S')

static_info = pandas.read_json("Hospital.json", lines=True)

all_info = pandas.merge(df, static_info, on='Hospital',how='inner')
all_info.to_json("georgiarcc.json", orient='records', lines=True)

#df.to_json("georgiarcc.json", orient='records', lines=True)

#print(df)
