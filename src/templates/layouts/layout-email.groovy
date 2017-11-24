html(lang:'en') {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		title(title)
		style{  yieldUnescaped """ 
	body {
			font-family:monospace
		}
		table {
			margin: auto;
			width: 600px;
			border-collapse: collapse;
			border: 1px solid #fff; /*for older IE*/
			border-style: hidden;
		}
		table caption {
			background-color: #3a5aa0;
			color: #fff;
			font-size: x-large;
			font-weight: bold;
			letter-spacing: .3em;
		}
		table thead th {
			padding: 8px;
			background-color: #b2c6f33d;
			font-size: large;
		}
		table th, table td {
			padding: 3px;
			border-width: 1px;
			border-style: solid;
			border-color: #3a5aa0 #ccc;
			text-align: center;
		}
			""" }
	}
	body { bodyContents() }
}
