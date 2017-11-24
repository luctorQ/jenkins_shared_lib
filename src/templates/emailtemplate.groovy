html(lang:'en') {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		title('My page')
		style{ yieldUnescaped """ 
			p {
				color: red;
				font-size: 46px
			  }
			""" }
	}
	body {
		p('This is an example of HTML contents from class loader')
		includeGroovy('templates/parts/includedtpl.groovy')
	}
}