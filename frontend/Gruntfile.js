module.exports = function(grunt) {
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),

		clean: ['build'],

		copy: {
			main: {
				files: [
					{
						// includes files within path and its sub-directories
						expand: true,
						cwd: 'app',
						src: ['**'],
						dest: 'build/'
					}
				]
			}
		},

		htmlmin: {
			dist: {
				options: {
					removeComments: true,
					collapseWhitespace: true
				},
				files: [{
					expand: true,
					cwd: 'build/views/',
					src: '**/*.html',
					dest: 'build/views/templates/'
				}]
			}
		},

		html2js: {
			options: {
				// custom options, see below
			},
			main: {
				src: ['build/views/templates/**/*.html'],
				dest: 'build/views/templates/templates.js'
			}
		},

		jshint: {
			all: ['Gruntfile.js', 'build/js/*.js', 'build/views/**/*.js', 'build/components/**/*.js']
		},

		concat: {
			options: {
				separator: ";\n",
				stripBanners: true,
				banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/\n'
			},

			basic_and_extras: {
				files: {
					'build/js/<%= pkg.name %>.js': [
						'build/node_modules/angular/angular.js',
						'build/node_modules/angular-animate/angular-animate.js',
						'build/node_modules/angular-drag-and-drop-lists/angular-drag-and-drop-lists.js',
						'build/node_modules/angular-touch/angular-touch.js',
						'build/node_modules/angular-ui-bootstrap/dist/ui-bootstrap.js',
						'build/node_modules/angular-ui-bootstrap/dist/ui-bootstrap-tpls.js',
						'build/node_modules/angular-ui-router/release/angular-ui-router.js',

						'build/js/app.js',

						'build/components/**/*.js',
						'build/js/controllers/**/*.js',
						'build/js/directives/**/*js',
						'build/js/services/**/*.js',
						'build/views/**/*.js'
					]
				}
			}
		},

		concat_css: {
			options: {
				separator: "\n",
				stripBanners: true,
				banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/\n'
			},

			all: {
				src: [
					'build/node_modules/angular-ui-bootstrap/dist/ui-bootstrap-csp.css',
					'build/node_modules/bootstrap/dist/css/bootstrap.css',
					'build/node_modules/bootstrap/dist/css/bootstrap-theme.css',

					'build/css/main.css',
					'build/css/reset.css',
					'build/views/**/*.css',
					'build/components/**/*.css'
				],
				dest: 'build/css/<%= pkg.name %>.css'
			}
		},

		cssmin: {
			add_banner: {
				options: {
					banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/'
				},
				files: {
					'build/css/<%= pkg.name %>.min.css': ['build/css/<%= pkg.name %>.css']
				}
			}
		},

		uglify: {
			options: {
				stripBanners: true,
				banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/\n'
			},
			my_target: {
				files: [{
					'build/js/<%= pkg.name %>.min.js' : ['build/js/<%= pkg.name %>.js']
				}]
			}
		},

		watch: {
			options: {
				livereload: true
			},
			html: {
				files: ['app/views/**/*.html'],
				tasks: ['default']
			},
			css: {
				files: ['app/css/main.css', 'app/css/reset.css', 'app/views/**/*.css'],
				tasks: ['default']
			},
			scripts: {
				files: ['app/js/app.js', 'app/views/**/*.js', 'app/components/**/*.js'],
				tasks: ['default']
			}
		},

		removelogging: {
			dist: {
				src: 'build/js/<%= pkg.name %>.min.js',
				dest: 'build/js/<%= pkg.name %>.clean.js'
			}
		}
	});

	grunt.loadNpmTasks('grunt-concat-css');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-contrib-htmlmin');
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-html2js');
	grunt.loadNpmTasks('grunt-nghtml-uglify');
	grunt.loadNpmTasks('grunt-remove-logging');

	grunt.registerTask('default', ['clean', 'copy', 'htmlmin', 'html2js', 'jshint', 'concat', 'concat_css', 'cssmin', /*'uglify', */'removelogging']);
	grunt.registerTask('watcher', ['watch']);
};
























