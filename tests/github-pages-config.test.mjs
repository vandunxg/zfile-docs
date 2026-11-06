import assert from 'node:assert/strict';
import { readFile } from 'node:fs/promises';
import test from 'node:test';

import docusaurusConfig from '../docusaurus.config.js';

test('configures the docs for the GitHub Pages project URL', () => {
  assert.equal(docusaurusConfig.url, 'https://vandunxg.github.io');
  assert.equal(docusaurusConfig.baseUrl, '/zfile-docs/');
});

test('deploys the built docs through GitHub Pages Actions', async () => {
  const workflow = await readFile('.github/workflows/deploy.yml', 'utf8');

  assert.match(workflow, /push:\s*\n\s+branches:\s*\n\s+[-'] main/);
  assert.match(workflow, /workflow_dispatch:/);
  assert.match(workflow, /pages: write/);
  assert.match(workflow, /id-token: write/);
  assert.match(workflow, /yarn install --frozen-lockfile/);
  assert.match(workflow, /run: yarn test/);
  assert.match(workflow, /run: yarn build/);
  assert.match(workflow, /actions\/upload-pages-artifact@v3/);
  assert.match(workflow, /path: \.\/build/);
  assert.match(workflow, /deploy:\s*\n\s+needs: build/);
  assert.match(workflow, /actions\/deploy-pages@v4/);
});
