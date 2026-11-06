// @ts-check

/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
    docsSidebar: [
        'intro',
        {
            type: 'category',
            label: 'Business Core',
            items: [
                'business/core/system-purpose',
                'business/core/business-model',
                'business/core/access-and-visibility',
                'business/core/core-workflows',
                'business/core/business-rules-and-scenarios',
            ],
        },
        {
            type: 'category',
            label: 'Business',
            items: [
                'business/feature-catalog',
                'business/overview',
                'business/installation-and-system-state',
                'business/storage-sources',
                'business/file-operations',
                'business/access-control',
                'business/sharing-and-links',
                'business/identity-and-administration',
                'business/system-configuration-and-operations',
                'business/preview-and-integrations',
                'business/permission-matrix',
                'business/provider-capability-matrix',
                'business/business-rules-catalog',
                'business/acceptance-scenarios',
                'business/domain-map',
                'business/glossary',
            ],
        },
        {
            type: 'category',
            label: 'Engineering',
            items: [
                'engineering/architecture-overview',
                'engineering/code-navigation',
                'engineering/code-conventions',
                'engineering/code-flows',
                'engineering/reusable-components',
                'engineering/data-and-configuration',
            ],
        },
        {
            type: 'category',
            label: 'Onboarding',
            items: [
                'onboarding/start-here',
                'onboarding/first-contribution',
                'onboarding/code-contribution-guide',
            ],
        },
        {
            type: 'category',
            label: 'Tài liệu cho team',
            items: [
                'roadmap/business-engineering',
                'engineering/documentation-standards',
            ],
        },
    ],
};

module.exports = sidebars;
