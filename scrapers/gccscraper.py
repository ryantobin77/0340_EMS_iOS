from bs4 import BeautifulSoup
import pandas
import requests

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
df.to_json("georgiarcc.json", orient='records', lines=True)

print(df)
