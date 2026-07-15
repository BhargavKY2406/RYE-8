import urllib.request, re;
try:
    req = urllib.request.Request('https://zyra.vercel.app/', headers={'User-Agent': 'Mozilla/5.0'})
    html = urllib.request.urlopen(req).read().decode('utf-8');
    js_files = re.findall(r'src=\"/assets/(.*?\.js)\"', html);
    print('Found JS files:', js_files);
    for f in js_files:
        js = urllib.request.urlopen('https://zyra.vercel.app/assets/' + f).read().decode('utf-8');
        urls = re.findall(r'https://[a-zA-Z0-9-]+\.onrender\.com/api', js);
        if urls:
            print('Found API URL in JS:', urls[0]);
            api_url = urls[0];
            print('Pinging API:', api_url + '/restaurants');
            api_res = urllib.request.urlopen(api_url + '/restaurants').read().decode('utf-8');
            print('API Response starts with:', api_res[:100]);
            break;
except Exception as e:
    print('Error:', e)
