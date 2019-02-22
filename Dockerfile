FROM nginx:1.12
COPY . /usr/share/nginx/html
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone
RUN chown -R nginx:nginx /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
