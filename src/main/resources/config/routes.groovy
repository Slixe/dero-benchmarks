package config;

group '/api', {
	get '/benchmarks', 'main:benchmarks'
	post '/submit', 'main:submit'

	group '', {
		get '/unconfirmedBenchmarks'
		post '/confirm'
		post '/delete'
	}, [
		action: 'main',
		middleware: 'auth'
	]
}

group '/api/auth', {
	//not recognized in group /api
	post '/validate'
	post '/login'
	post '/logout'
}, [
	action: 'auth'
]