import urllib.request, re;
try:
    req = urllib.request.Request('https://zyra.vercel.app/', headers={'User-Agent': 'Mozilla/5.0'})
    html = urllib.request.urlopen(req).read().decode('utf-8');
    js_files = re.findall(r'src=\"/assets/(.*?\.js)\"', html);
    for f in js_files:
        js = urllib.request.urlopen('https://zyra.vercel.app/assets/' + f).read().decode('utf-8');
        if 'zyra_home_cache' in js:
            print('CACHE FIX IS IN THE LIVE JS!')
        else:
            print('Not found in this JS file.')
except Exception as e:
    print('Error:', e)
