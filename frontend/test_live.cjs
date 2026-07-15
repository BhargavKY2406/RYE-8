const { chromium } = require('playwright');
(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();
  page.on('console', msg => console.log('BROWSER LOG:', msg.type(), msg.text()));
  page.on('pageerror', err => console.log('BROWSER ERROR:', err.message));
  await page.goto('https://zyra.vercel.app/');
  await new Promise(r => setTimeout(r, 4000));
  await browser.close();
})();
