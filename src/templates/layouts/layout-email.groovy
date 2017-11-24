html(lang:'en') {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		title(title)
		style{  yieldUnescaped """ 
		table {
			margin: auto;
			//width: 600px;
			border-collapse: collapse;
			border: 1px solid #fff; /*for older IE*/
			border-style: hidden;
		}
		table caption {
			background-color: #f79646;
			color: #fff;
			font-size: x-large;
			font-weight: bold;
			letter-spacing: .3em;
		}
		table thead th {
			padding: 8px;
			background-color: #fde9d9;
			font-size: large;
		}

			""" }
	}
	body { bodyContents() }
}
