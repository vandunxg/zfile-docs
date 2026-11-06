// @ts-check

/** @type {import('@docusaurus/types').Config} */
const config = {
    title: 'Tài liệu ZFile',
    tagline: 'Business và Engineering Documentation',
    url: 'https://docs.zfile.vip',
    baseUrl: '/',
    onBrokenLinks: 'throw',
    i18n: {
        defaultLocale: 'vi',
        locales: ['vi'],
    },
    clientModules: [require.resolve('./src/clientModules/mermaid-transition.js')],
    markdown: {
        mermaid: true,
    },
    presets: [
        [
            'classic',
            /** @type {import('@docusaurus/preset-classic').Options} */
            ({
                docs: {
                    sidebarPath: require.resolve('./sidebars.js'),
                    routeBasePath: '/',
                    exclude: ['superpowers/**'],
                },
                blog: false,
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
            }),
        ],
    ],
    themes: ['@docusaurus/theme-mermaid'],
};

module.exports = config;
