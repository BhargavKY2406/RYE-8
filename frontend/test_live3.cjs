const { chromium } = require('playwright');
(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();
  await page.goto('https://zyra.vercel.app/');
  await new Promise(r => setTimeout(r, 4000));
  await page.screenshot({ path: 'live_screenshot_full.png', fullPage: true });
  await browser.close();
})();
