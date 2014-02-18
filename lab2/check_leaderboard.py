#!/usr/bin/env python

import datetime
import urllib

from bs4 import BeautifulSoup

url = 'http://gauss.ececs.uc.edu/standings8180.html'
page = urllib.urlopen(url)
soup = BeautifulSoup(page)

p = soup.findAll('p')[:4]
updated = p[2].text
updated = datetime.datetime.strptime(updated, "%a %b %d %H:%M:%S %Z %Y")
now = datetime.datetime.now()
diff = now-updated
print 'Last updated: %s seconds ago' % (diff.total_seconds())

tables = soup.findAll('table')
rows = tables[1].findAll('tr')
skip = 0
for r in rows:
    tds = r.findAll('td', text=True)

    if not tds:
        skip += 1
        continue

    name = tds[0].text
    if name == "JOHNE":
        pts = tds[2].text
        print 'PTS:', pts
        break
