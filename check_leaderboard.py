#!/usr/bin/env python

import datetime
import urllib
import sys

from bs4 import BeautifulSoup

url = 'http://gauss.ececs.uc.edu/standings8180.html'

def fetch_html(url):
    return urllib.urlopen(url)

def parse_html(html):
    return BeautifulSoup(html)

def last_update_time(soup):
    p = soup.findAll('p')[2]
    return datetime.datetime.strptime(p.text, "%a %b %d %H:%M:%S %Z %Y")

def secs_since_update(last_updated):
    return (datetime.datetime.now() - last_updated).total_seconds()

def find_points(soup, user):
    table = soup.findAll('table')[1]
    rows = table.findAll('tr')
    for r in rows:
        tds = r.findAll('td', text=True)
        if not tds:
            continue
        # check is desired user
        if tds[0].text.lower() == user.lower():
            return tds[2].text

def main(args):
    user = args[0]
    html = fetch_html(url)
    soup = parse_html(html)
    update_time = last_update_time(soup)
    print 'Last updated: %s seconds ago' % (secs_since_update(update_time))
    print '%s\'s points: %s' % (user, find_points(soup, user))

if __name__ == '__main__':
    main(sys.argv[1:])
