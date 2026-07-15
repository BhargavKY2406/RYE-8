import urllib.request, re;
try:
    req = urllib.request.Request('https://zyra.vercel.app/', headers={'User-Agent': 'Mozilla/5.0'})
    html = urllib.request.urlopen(req).read().decode('utf-8');
    js_files = re.findall(r'src=\"/assets/(.*?\.js)\"', html);
    for f in js_files:
        js = urllib.request.urlopen('https://zyra.vercel.app/assets/' + f).read().decode('utf-8');
        if 'filterUnique' in js:
            print('filterUnique IS IN THE LIVE JS!')
            
        if 'hasCache' in js:
            print('hasCache fix IS IN THE LIVE JS!')
            
except Exception as e:
    print('Error:', e)
