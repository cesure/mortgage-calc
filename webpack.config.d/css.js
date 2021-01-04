config.resolve.modules.push("processedResources/js/main");

config.module.rules.push({
    test: /\.css$/,
    use: [
        {
            loader: 'style-loader',
        },
        {
            loader: 'css-loader',
        },
        {
            loader: 'postcss-loader',
            options: {
                postcssOptions: {
                    plugins: [
                        require('postcss-import')({
                            path: '../../node_modules'
                        }),
                        require('tailwindcss')({
                            purge: {
                                enabled: true,
                                content: ['../../../../src/jsMain/kotlin/**/*.kt'],
                            },
                        }),
                        require('autoprefixer'),
                    ],
                }
            },
        },
    ],
});
