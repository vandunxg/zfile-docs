import test from 'node:test';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
import {readFile} from 'node:fs/promises';
import {resolve} from 'node:path';

const require = createRequire(import.meta.url);

test('loads D3 transitions before Mermaid tooltip handlers run', async () => {
  const config = require('../docusaurus.config.js');
  const transitionModule = resolve('src/clientModules/mermaid-transition.js');

  assert.ok(config.clientModules?.includes(transitionModule));
  assert.match(await readFile(transitionModule, 'utf8'), /import ['"]d3-transition['"]/);

  await import('d3-transition');
  const {select} = await import('d3-selection');

  assert.equal(typeof select(null).transition, 'function');
});
